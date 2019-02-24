function reloadPage(){
    document.location.reload(true);
}

function insertInGuestBook(name, comment){
    var xhttp = new XMLHttpRequest();

    xhttp.open("POST", "../v1/VisitorDB", true);
    xhttp.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
    xhttp.send("insert=&name="+name+"&comment="+comment);
    setTimeout(reloadPage,500);

}

function loadGuestBook() {

    var xhttp = new XMLHttpRequest();
    xhttp.onreadystatechange = function(){
        if(this.readyState===4){
            var visitorsJSON = JSON.parse(this.responseText);

            var iframe = document.getElementById('iframe_guestBook');
            var iframedoc = iframe.document;
            if (iframe.contentDocument)
                iframedoc = iframe.contentDocument;
            else if (iframe.contentWindow)
                iframedoc = iframe.contentWindow.document;

            if(iframedoc) {
                iframedoc.open();
                var myTable = document.createElement('table');
                myTable.width = 550;
                myTable.style = 'font-family:"Calibri", serif; font-size:12px; color:white'

                var htmlstring1 ='<html><head><body style="margin: 0;padding: 0;" bgcolor="#6495ed">';
                var htmlstring2 = '</body></head></html>';
                for (var i = 0; i < visitorsJSON.length; i++) {
                    console.log("prutt")

                    var tableRow = document.createElement('tr');
                    var rowItem1 = document.createElement('td');
                    rowItem1.width = 20;
                    var rowItem2 = document.createElement('td');
                    rowItem2.width = 100;
                    var rowItem3 = document.createElement('td');
                    rowItem3.width = 300;
                    rowItem1.textContent = visitorsJSON[i].visitorNr;
                    rowItem2.textContent = visitorsJSON[i].name;
                    rowItem3.textContent = visitorsJSON[i].comment;
                    tableRow.appendChild(rowItem1);
                    tableRow.appendChild(rowItem2);
                    tableRow.appendChild(rowItem3);
                    myTable.appendChild(tableRow);
                }

                var htmlStringComplete = htmlstring1 + myTable.outerHTML + htmlstring2;

                iframedoc.writeln(htmlStringComplete);

                iframedoc.close();

            }


        }


    };
    xhttp.open("GET", "/v1/VisitorDB?getAll=", true);
    xhttp.send();
}
