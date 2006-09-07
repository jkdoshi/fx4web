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

package com.doshiland.fx4web;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class ResourceFilter implements Filter {
	private static final Log log = LogFactory.getLog(ResourceFilter.class);

	private ServletContext servletContext;

	public void init(FilterConfig filterConfig) throws ServletException {
		log.debug("Initializing");
		servletContext = filterConfig.getServletContext();
	}

	public void doFilter(ServletRequest req, ServletResponse resp,
			FilterChain chain) throws IOException, ServletException {
		HttpServletResponse response = (HttpServletResponse) resp;
		HttpServletRequest request = (HttpServletRequest) req;
		String path = request.getRequestURI().substring(request.getContextPath().length());
		URL url = servletContext.getResource(path);
		// if the URL was found, then the context has the resource
		if (url != null) {
			chain.doFilter(req, resp);
		} else {
			// if the dispatcher was not found, then this filter should look for
			// it
			log.debug("Serving resource: " + path);
			url = ResourceFilter.class.getResource(path);
			if (url != null) {
				URLConnection conn = url.openConnection();
				InputStream in = conn.getInputStream();
				response.setContentType(servletContext.getMimeType(path));
				response.setDateHeader("Last-Modified", conn.getLastModified());
                response.setHeader("Cache-Control", "No-Cache");
				BufferedInputStream bin = new BufferedInputStream(in);
				ServletOutputStream out = response.getOutputStream();
				byte[] buf = new byte[1024];
				int count;
				while ((count = bin.read(buf)) > 0) {
					out.write(buf, 0, count);
				}
				out.close();
				bin.close();
				in.close();
			} else {
				log.warn("Resource not found: " + path);
				response.sendError(HttpServletResponse.SC_NOT_FOUND);
			}
		}
	}

	public void destroy() {
		log.debug("Destroying");
	}

}
