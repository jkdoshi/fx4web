PROJECT: Doshiland Web Library
------------------------------

fx4web toolkit is a general purpose collection of tools that can be used
in web-application development. Some of the features provided are ...
- implementation of a "conversation" scope (a scope that is in-between
  "request" and "session" scope of the servlet API). This is very useful
  if your web-application opens multiple windows showing the same UI page
  but with different data and needs to remember it's state.
- A generic DHTML "floating window" showing application generated messages.
  The application delivers the messages in form of a JavaScript array variable
  and the client-side JavaScript generates the HTML UI on-the-fly. For
  web-applications that use JSF, a pre-packaged PhaseListener is provided
  that does all the work. For non-JSF applications, one can be written very
  easily.
- DHTML code to make any table "scrollable". All you have to do is
  assign it a specific CSS class name.
- DHTML code to generate a "silkscreen" (a layer that blocks user interaction
  the UI). This is useful in disabling a UI during server trips and also
  for showing modal psuedo-popups.

... and many more features.

More detailed information is available in the generated javadocs. Please
run "ant dist" to get a full distribution which includes the javadocs.
