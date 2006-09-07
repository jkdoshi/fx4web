function getfiles(fileInput) {
	fileInput = $(fileInput);
	var fso = new ActiveXObject("Scripting.FileSystemObject");
	var folderName = fso.GetParentFolderName(fileInput.value)
	var folder = fso.GetFolder(folderName);
	alert(folder.DateLastModified);
	for(prop in folder) {
		alert(prop);
	}
	var files = folder.Files;
	for(var i = 0; i < files.length; i++) {
		alert(files[i]);
	}
}
