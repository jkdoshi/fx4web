/*
 * Copyright (c) 2006 - Jitesh Doshi (jitesh@doshiland.com)
 * Please see http://www.doshiland.com/ for terms of use.
 */

/* Make window.addEventListener available to those browsers that don't have it (IE) */
if(!window.addEventListener) {
	// Internet Explorer
	window.addEventListener = function(eventName, handler, capture) {
		if(this.attachEvent) {
			this.attachEvent("on" + eventName, handler);
		}
	}
}

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

function addParamToLinks(fldname) {
	var links = document.links;
	for(var i = 0; i < links.length; i++) {
		var link = links[i];
		if(link.href != '#') {
			if(link.search) {
				link.search += '&' + fldname + '=' + window.name;
			} else {
				link.search = fldname + '=' + window.name;
			}
		}
	}
}

/**
 * Instrument the window.open method such that the cid is added to the URL
 * as search query string param.
 */
var originalWindowOpen;

function enhancedWindowOpen(url, windowName, options, replace) {
	if(arguments.length > 1) {
		url = url + "?_cid=" + windowName;
	}
	if(arguments.length > 3) {
		return originalWindowOpen(url, windowName, options, replace);
	} else if(arguments.length == 3) {
		return originalWindowOpen(url, windowName, options);
	} else if(arguments.length == 2) {
		return originalWindowOpen(url, windowName);
	} else {
		return originalWindowOpen(url);
	}
}

// override only once
if(typeof originalWindowOpen == 'undefined'
		&& window.open != enhancedWindowOpen) {
	originalWindowOpen = window.open;
	if(originalWindowOpen != enhancedWindowOpen) {
		window.open = enhancedWindowOpen;
	}
}

function insertConversationId() {
	var fldname = '_cid';
	var cid = window.name;
	if(cid) {
		/**
		 * Add window name as param to forms and links only if window.name is
		 * non-empty. This will be the case for child windows.
		 */
		addParamToForms(fldname, cid);
		addParamToLinks(fldname, cid);
	}
}

window.addEventListener("load", insertConversationId, true);