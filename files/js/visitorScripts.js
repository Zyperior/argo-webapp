var xhttp = new XMLHttpRequest();

function reloadPage(){
    document.location.reload(true);
}

function insertInGuestBook(name, comment){
    xhttp.onreadystatechange = function () {

    };
    xhttp.open("GET", "../v1/VisitorDB?insert=&name=" + name + "&comment=" + comment, true);
    xhttp.send();
    setTimeout(reloadPage,500);

}

function loadGuestBook() {

    xhttp.onreadystatechange = function(){

        var visitorsJSON = JSON.parse(this.responseText);

        var iframe = document.getElementById('iframe_guestBook');
        var html_string = '<html><head></head><body></body></html>';
        var iframedoc = iframe.document;
        if (iframe.contentDocument)
            iframedoc = iframe.contentDocument;
        else if (iframe.contentWindow)
            iframedoc = iframe.contentWindow.document;

        if (iframedoc){
            // Put the content in the iframe
            iframedoc.open();
            iframedoc.writeln(html_string);
            var myTable = document.createElement('table');
            for(var i = 0; i < visitorsJSON.length; i++){
                var tableRow = document.createElement('tr');
                var rowItem1 = document.createElement('td');
                var rowItem2 = document.createElement('td');
                var rowItem3 = document.createElement('td');

                rowItem1.textContent = visitorsJSON[i].visitorNr;
                rowItem2.textContent = visitorsJSON[i].name;
                rowItem3.textContent = visitorsJSON[i].comment;
                tableRow.appendChild(rowItem1);
                tableRow.appendChild(rowItem2);
                tableRow.appendChild(rowItem3);
                myTable.appendChild(tableRow);
            }


            iframedoc.body.appendChild(myTable);
            iframedoc.close();
        }

    };
    xhttp.open("GET", "v1/VisitorDB?getAll=", true);
    xhttp.send();
}
window.onload = loadGuestBook();