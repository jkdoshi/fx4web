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