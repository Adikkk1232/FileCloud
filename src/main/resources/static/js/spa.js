updateListOfFileNames();

$(".getAllFilesInZip").on("click", function(event) {
    $.get("/api/files/zip", function(data, textStatus) {
        if($("#fileList li").length === 0) {
            // There isn't any files
            alert("We don't have stored any files!")
        } else {
            // TODO: Fix zip!!!!
            var zipBlob = new Blob(new Array(data), {type: "application/zip"});

            console.log(zipBlob);

            var virtualLink = document.createElement("a");
            virtualLink.href = window.URL.createObjectURL(zipBlob);
            virtualLink.download = "files.zip";
            virtualLink.click();
            virtualLink.remove();
        }
    });
});

$("#fileList").on("click", "li button.btnDownload", function(event) {
    var fileName = $(this).attr("data-filename");

    $.get("/api/file?name=" + fileName, function(data, textStatus) {
       if(textStatus === "success") {
           var fileObject = JSON.parse(JSON.stringify(data));

           var zipBlob = new Blob([fileObject.bytes]);

           var virtualLink = document.createElement("a");
           virtualLink.href = window.URL.createObjectURL(zipBlob);
           virtualLink.download = fileName;
           virtualLink.click();
           virtualLink.remove();
       } else {
           // File not found
           alert(fileName + " not found!");
       }
    }).fail(function() {
        // File not found
        alert(fileName + " not found!");
    });
});

$("#fileList").on("click", "li button.btnDelete", function(event) {
    var fileName = $(this).attr("data-filename");

    $.ajax({
        url: "/api/file?name=" + fileName,
        type: "DELETE",
        success: function(data, textStatus) {
            if(textStatus === "success") {
                alert(fileName + " deleted!");
                updateListOfFileNames();
            } else {
                // File not found
                alert(fileName + " not found!");
            }
        },
        error: function(error) {
            // File not found
            alert(fileName + " not found!");
        }
    });
});

function updateListOfFileNames() {
// Download the freshest data about list of files from server
    $.get("/api/files/names", function (data, textStatus) {
        if (textStatus === "nocontent") {
            // There isn't any files
            $("#noFilesFoundParagraph").show();
            $("#fileList li").remove();
            $("#filesCountDiv").text("0 files in total");
        } else {
            $("#noFilesFoundParagraph").hide();

            data.forEach(function (fileName) {
                var createdListElement = '<li class="list-group-item"><span class="fileNameContainer">'
                    + fileName + '</span> <button type="button" data-filename="' + fileName + '" class="btnDownload btn-success"><i class="fa fa-upload"></i></button>'
                    + ' <button type="button" data-filename="' + fileName + '" class="btnDelete btn-danger">X</button></li>';

                $("#fileList").html($("#fileList").html() + createdListElement);
            });

            $("#filesCountDiv").text(data.length === 1 ? "1 file in total" : (data.length + " files in total"));
        }
    });
}

// TODO: Add upload
// TODO: Add storage used progress bar handlers
