/*
 * Copyright (c) 2006 Jitesh Doshi
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may
 * not use this file except in compliance with the License. You may obtain
 * a copy of the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations
 * under the License.
 */

/*
 * This library depends on the Prototype library (http://prototype.conio.net)
 * version 1.4 or later. So you must include prototype.js before including
 * this JS.
 */

/* --------------------- Main Library ----------------------------*/

var FX4Web = new Object();

/* the function that is registered to execute on window.load event */
FX4Web.onload = function() {
	/* instrument the sloshbuckets */
	var sels = document.getElementsByClassName('fx4web-sloshbucket');
	for(var i = 0; i < sels.length; i++) {
		var sel = sels[i];
		if(sel.tagName == 'SELECT') {
			FX4Web.sloshBucket(sel);
		}
	}
	/* instrument the floating elements */
	var fls = document.getElementsByClassName('fx4web-floating');
	for(var i = 0; i < fls.length; i++) {
		new FX4Web.Floating(fls[i]);
	}
	/* instrument the windows, links and forms with the conversation-id */
	FX4Web.initConversations();
}

/* wrap an element in another tag */
FX4Web.wrap =  function(node, wrapperTagName) {
    var wrapper = document.createElement(wrapperTagName);
    if(node.parentNode) {
        node.parentNode.replaceChild(wrapper, node);
    }
    wrapper.appendChild(node);
    return wrapper;
}

/* find the label element for a given input field */
FX4Web.getLabelFor =  function(input) {
    input = $(input);
    if(input) {
        var labels = document.getElementsByTagName('LABEL');
        for(var i = 0; i < labels.length; i++) {
            if(labels[i].htmlFor == input.id) {
                return labels[i];
            }
        }
    }
    return null;
}

/* determine the label TEXT for a given input field */
FX4Web.getLabelTextFor =  function(input) {
    var label = this.getLabelFor(input);
    if(label && label.firstChild) {
        return label.firstChild.nodeValue;
    }
    return null;
}

/* find out if child is a decendent of parent */
FX4Web.isDecendentOf = function(parent, child) {
	parent = $(parent);
	child = $(child);
	if(!parent || !child) {
		return false;
	}
	while(child.parentNode != null) {
		if(child.parentNode == parent) {
			return true;
		} else {
			child = child.parentNode;
		}
	}
	return false;
}

/* make the given container invisible after it was shown as a popup */
FX4Web.hidePopup =  function(container) {
    container = $(container);
    container.style.display = 'none';
    var ssDiv = container.silkscreen;
    if(ssDiv && ssDiv.parentNode) {
	    ssDiv.parentNode.removeChild(ssDiv);
    }
    // restore buggy controls
    if(document.buggyControls) {
		for(var i = 0; i < document.buggyControls.length; i++) {
			var control = document.buggyControls[i];
			control['elem'].style.visibility = control['visibility'];
		}
	}
	document.buggyControls = [];
    //document.body.style.filter = '';
}

/* show the given container element as a popup on top of a silkscreen */
FX4Web.showPopup =  function(container) {
    container = $(container);
    container.silkscreen = this.silkscreen();
    // hide buggy controls
    var elems = document.getElementsByTagName('SELECT');
	document.buggyControls = [];
    for(var i = 0; i < elems.length; i++) {
    	var elem = elems[i];
    	// don't disturb what is inside the container
    	if(!this.isDecendentOf(container, elem)) {
    		// save the old style
    		document.buggyControls.push({'elem': elem, 'visibility': elem.style.visibility});
    		// set the new style
    		elem.style.visibility = 'hidden';
    	}
    }
    
    container.style.display = 'block';
    this.center(container);
    //document.body.style.filter = 'Gray';
}

/* make all the options selected in a given select listbox */	
FX4Web.selectAllOptions =  function(sel) {
    sel = $(sel);
    for(var i = 0; i < sel.length; i++) {
        sel.options[i].selected = true;
    }
}

/* remove all the selected or unselected options from a listbox */
FX4Web.removeCertainOptions =  function(sel, selected) {
    sel = $(sel);
    for(var i = 0; i < sel.length; i++) {
        var opt = sel.options[i];
        if(opt.selected == selected) {
            sel.remove(i);
            --i; // because we disturbed the options by removing one
        }
    }
}

/* move all the selected or unselected options from one listbox to another */
FX4Web.moveCertainOptions =  function(src, dst, selected) {
    for(var i = 0; i < src.length; i++) {
        var opt = src.options[i];
        if(opt.selected == selected) {
            var newOpt = new Option(opt.text, opt.value);
            newOpt.selected = selected;
            dst.options.add(newOpt);
            src.remove(i);
            --i; // because we disturbed the options by removing one
        }
    }
}

/* move all the selected options from one listbox to another */
FX4Web.moveSelectedOptions =  function(src, dst) {
    this.moveCertainOptions(src, dst, true);
}

/* turn a given select list box into a sloshbucket */
FX4Web.sloshBucket =  function(sel) {
    // use the given select list box as the destination
    var selDst = $(sel);
    // create source select list box
    var selSrc = document.createElement('SELECT');
    selSrc.id = selDst.id + '-src';
    selSrc.className = selDst.className;
    selSrc.size = selDst.size;
    selSrc.multiple = selDst.multiple;
    this.moveCertainOptions(selDst, selSrc, false);
    // create the add button
    var btnAdd = document.createElement('INPUT');
    btnAdd.id = selDst.id + '-add';
    btnAdd.className = selDst.className;
    btnAdd.type = 'BUTTON';
    btnAdd.value = '>>';
    // create the remove button
    var btnRem = document.createElement('INPUT');
    btnRem.id = selDst.id + '-rem';
    btnRem.className = selDst.className;
    btnRem.type = 'BUTTON';
    btnRem.value = '<<';
    // wire them up
    this.sloshBucketWireUp(selSrc, selDst, btnAdd, btnRem);

    // AND NOW create the container
    var tbl = document.createElement('TABLE');
	tbl.className = selDst.className;
    var parent = selDst.parentNode;
    parent.insertBefore(tbl, selDst);
    var tbody = document.createElement('TBODY');
    var tr = document.createElement('TR');
    var td1 = document.createElement('TD');
    var td2 = document.createElement('TD');
    var td3 = document.createElement('TD');
    td1.appendChild(selSrc);
    td2.appendChild(btnAdd);
    td2.appendChild(document.createElement('BR'));
    td2.appendChild(btnRem);
    td3.appendChild(selDst);
    tr.appendChild(td1);
    tr.appendChild(td2);
    tr.appendChild(td3);
    tbody.appendChild(tr);
    tbl.appendChild(tbody);
}

/* wire-up four pre-existing elements (two buttons and two listboxes) into a sloshbucket */
FX4Web.sloshBucketWireUp =  function(selSrc, selDst, btnAdd, btnRem) {
    selSrc = $(selSrc);
    selDst = $(selDst);
    btnAdd = $(btnAdd);
    btnRem = $(btnRem);
    /* wire up the buttons */
    Event.observe(btnAdd, 'click', function() {
        FX4Web.moveSelectedOptions(selSrc, selDst);
        return false;
    });
    Event.observe(btnRem, 'click', function() {
        FX4Web.moveSelectedOptions(selDst, selSrc);
        return false;
    });
    /* make sure the second SELECT will have everything selected
       when the parent form submits */
    Event.observe(selDst.form, 'submit', function() {
        FX4Web.selectAllOptions(selDst);
    });
    function newSubmit () {
        FX4Web.selectAllOptions(selDst);
        this._fx4web_orig_submit();
    }
    // override only once
    if(!selDst.form._fx4web_orig_submit) {
	    selDst.form._fx4web_orig_submit = selDst.form.submit;
    	selDst.form.submit = newSubmit;
    }
}

/* not needed: REMOVE */
FX4Web.jsfSloshBucket =  function(selSrc, selDst, btnAdd, btnRem) {
    selSrc = $(selSrc);
    selDst = $(selDst);
    /* for JSF, we have to remove all selected from src and all unselected from dst */
    this.removeCertainOptions(selSrc, true);
    this.removeCertainOptions(selDst, false);
    this.sloshBucket(selSrc, selDst, btnAdd, btnRem);
}

/* cover the whole page with a translucent DIV that prevents user interaction */
FX4Web.silkscreen =  function() {
    var viewport = this.getWindowSize();
    var ssDiv = document.createElement('DIV');
    ssDiv.id = 'silkscreen';
    ssDiv.style.width = viewport.width + "px";
    ssDiv.style.height = viewport.height + "px";
    document.body.appendChild(ssDiv);
    return ssDiv;
}

/* return the dimensions of the visible part of the current window */
FX4Web.getLocation =  function(elem) {
	elem = $(elem);
	return {
		x: elem.offsetLeft,
		y: elem.offsetTop
	}
}

/* return the dimensions of the visible part of the current window */
FX4Web.getViewport =  function() {
    if (self.innerHeight) // all except Explorer
    return {
        width: self.innerWidth,
        height: self.innerHeight
    }
    else if (document.documentElement && document.documentElement.clientHeight)
        // Explorer 6 Strict Mode
    return {
        width: document.documentElement.clientWidth,
        height: document.documentElement.clientHeight
    }
    else if (document.body) // other Explorers
    return {
        width: document.body.clientWidth,
        height: document.body.clientHeight
    }
}

/* return the dimensions of the current window (visible or not) */
FX4Web.getWindowSize =  function() {
    if (navigator.appName.indexOf("Microsoft") >= 0) // MS
    return {
        width: document.body.scrollWidth,
        height: document.body.scrollHeight
    }
    else if (document.documentElement && document.documentElement.offsetHeight)
    return {
        width: document.documentElement.offsetWidth,
        height: document.documentElement.offsetHeight
    }
}

/* position the element in the center of the visible part of the current window */
FX4Web.center =  function(elem) {
    elem = $(elem);
    var viewport = this.getViewport();
    var elemWidth = elem.clientWidth;
    var elemHeight = elem.clientHeight;
    elem.style.left = (viewport.width - elemWidth)/2 + "px";
    elem.style.top = (viewport.height - elemHeight)/2 + "px";
}

/* wrap decorations around the given node and make it draggable */
FX4Web.float =  function(node, options) { /* title is optional */
    var options = options ? options : {};
    var container = this.wrap(node, 'DIV');
    container.className = 'fx4web-floating';
    var dragHandle = container;
    if(options.title) {
        var titleBar = document.createElement('DIV');
        titleBar.className = 'fx4web-title';
        titleBar.appendChild(document.createTextNode(options.title));
        container.insertBefore(titleBar, node);
        dragHandle = titleBar;
        /*
        if(typeof options.minimizeButton == 'undefined') {
            options.minimizeButton = true;
        }
        */
        if(typeof options.closeButton == 'undefined') {
            options.closeButton = true;
        }
        if(options.minimizeButton) {
            var btn = document.createElement('BUTTON');
            btn.className = 'fx4web-minimize';
            btn.appendChild(document.createTextNode('-'));
            titleBar.appendChild(btn);
            Event.observe(btn, 'click', function(e) {
                if(node.style.display == 'none') {
                    node.style.display = 'block';
                } else {
                    node.style.display = 'none';
                }
            });
        }
        if(options.closeButton) {
            var btn = document.createElement('BUTTON');
            btn.className = 'fx4web-close';
            btn.appendChild(document.createTextNode('close'));
            container.appendChild(btn);
            Event.observe(btn, 'click', function(e) {
                container.parentNode.removeChild(container);
            });
        }
    }
    new FX4Web.Floating(container, dragHandle);
    return container;
}

/* display application messages stored in a javascript array */
FX4Web.showMessages =  function(messages, title) {
    var container = document.createElement('OL');
    for(var i = 0; i < messages.length; i++) {
        var li = document.createElement('LI');
        var msg = messages[i];
        var text = msg.detail ||  msg.summary;
        if(msg.clientId) {
            var labelText = this.getLabelTextFor(msg.clientId);
            if(labelText) {
                // text = labelText + ": " + text;
                text = text.replace(/^\"([^\"]*)\"/, labelText);
            }
        }
        var textNode = document.createTextNode(text);
        li.className = "fx4web-" + msg.severity;
        if(msg.clientId) {
            var child = document.createElement('A');
            child.appendChild(textNode);
            child.href = 'javascript:$("' + msg.clientId + '").focus()';
        } else {
            child = textNode;
        }
        li.appendChild(child);
        container.appendChild(li);
    }
    container = this.float(container, title);
    var body = document.body;
    if(document.body) {
        document.body.appendChild(container);
        FX4Web.center(container);
    } else {
        Event.observe(window, 'load', function() {
            document.body.appendChild(container);
            FX4Web.center(container);
        });
    }
    return container;
}

/* makes a block level element "floating" or draggable (optionally, with a drag handle) */
FX4Web.Floating = function(obj, handle) {
	this.obj = $(obj);
	if(handle) {
		this.handle = $(handle);
	} else {
		this.handle = this.obj;
	}
	this.obj.style.position = 'absolute';
	this.handle.style.cursor = 'move';
	Event.observe(this.handle, "mousedown", this.onmousedown.bind(this));
}

FX4Web.Floating.prototype.onmousedown = function(evt) {
	this.ref = { x: evt.clientX, y: evt.clientY};
	this.start = FX4Web.getLocation(this.obj);
	this.foo = 5;
	this._onmousemove = this.onmousemove.bind(this);
	this._onmouseup = this.onmouseup.bind(this);
	Event.observe(document, "mousemove", this._onmousemove);
	Event.observe(document, "mouseup", this._onmouseup);
}

FX4Web.Floating.prototype.onmousemove = function(evt) {
	this.displace(evt);
}

FX4Web.Floating.prototype.onmouseup = function(evt) {
	this.displace(evt);
	Event.stopObserving(document, "mousemove", this._onmousemove);
	Event.stopObserving(document, "mouseup", this._onmouseup);
}

FX4Web.Floating.prototype.displace = function(evt) {
	var dispX = Event.pointerX(evt) - this.ref.x;
	var dispY = Event.pointerY(evt) - this.ref.y;
	this.obj.style.left = (this.start.x + dispX) + "px";
	this.obj.style.top  = (this.start.y + dispY) + "px";
}

/* --------------------- Conversations ----------------------------*/
	
/**
 * Instrument the window.open method such that the cid is added to the URL
 * as search query string param.
 */
var originalWindowOpen;

/* determine the correct conversation id for the given windowName.
 * The window name could also be "_self", "_parent" etc. Also, if the
 * resulting window name has '__' (double underscore) characters in it,
 * only the part before the '__' will be used as the conversation id.
 * This charater is used to have different window names and yet be able
 * to share the same conversation id. */
function getConversationID(windowName) {
	var retval = windowName;
	if(windowName && windowName.indexOf("_") == 0) {
		var win = windowName.substring(1);
		try {
			retval = window[win].name;
		} catch {
			// a window by that name may not exist (no problem)
		}
	}
	/* now look for a special separator ('__') */
	if(retval && retval.indexOf('__') >= 0) {
		var idx = retval.indexOf('__');
		retval = retval.substring(0, idx);
	}
	return retval;
}

function enhancedWindowOpen(url, windowName, options, replace) {
	if(arguments.length > 1) {
		var cid = getConversationID(windowName);
		if(cid) {
			if(url.indexOf('?') > -1) {
				url = url + "&_cid=" + cid;
			} else {
				url = url + "?_cid=" + cid;
			}
		}
	}
	var newWin;
	if(arguments.length > 3) {
		newWin = originalWindowOpen(url, windowName, options, replace);
	} else if(arguments.length == 3) {
		newWin = originalWindowOpen(url, windowName, options);
	} else if(arguments.length == 2) {
		newWin = originalWindowOpen(url, windowName);
	} else {
		newWin = originalWindowOpen(url);
	}
	if(newWin) {
		newWin.focus();
	}
	return newWin;
}

// override only once
if(typeof originalWindowOpen == 'undefined'
		&& window.open != enhancedWindowOpen) {
	originalWindowOpen = window.open;
	if(originalWindowOpen != enhancedWindowOpen) {
		window.open = enhancedWindowOpen;
	}
}

FX4Web.initConversations = function() {
	function addParamToForms(fldname, fldvalue) {
		var forms = document.forms;
		for(var i = 0; i < forms.length; i++) {
			var form = forms[i];
			var input = form[fldname];
			if(!input) {
				input = document.createElement('INPUT');
				input.name = fldname;
				input.type = 'hidden';
				form.appendChild(input);
			}
			input.value = fldvalue;
		}
	}
	
	function isLinkInstrumentable(link) {
		var flag = typeof link.href != 'undefined'
			&& !(link.href.indexOf('#') == 0
				|| link.href.indexOf('javascript:') == 0);
		return flag;
	}
	
	function addParamToLinks(fldname, fldvalue) {
		var links = document.links;
		for(var i = 0; i < links.length; i++) {
			var link = links[i];
			/* instrument only relative URI references */
			if(isLinkInstrumentable(link)) {
				if(link.target) {
					fldvalue = getConversationID(link.target);
				}
				if(link.search) {
					link.search += '&' + fldname + '=' + fldvalue;
				} else {
					link.search = '?' + fldname + '=' + fldvalue;
				}
			}
		}
	}
	
	//function insertConversationId() {
		var fldname = '_cid';
		var cid = getConversationID(window.name);
		if(cid) {
			/**
			 * Add window name as param to forms and links only if window.name is
			 * non-empty. This will be the case for child windows.
			 */
			addParamToForms(fldname, cid);
			addParamToLinks(fldname, cid);
		}
	//}
}

Event.observe(window, "load", FX4Web.onload);
