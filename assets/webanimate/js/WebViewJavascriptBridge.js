/**
 * Created by houxb on 2015/10/30.
 */
function testDiv() {
    document.getElementById("show").innerHTML = document.getElementsByName("html")[0].innerHTML;
}

function testClick() {
    var str1 = document.getElementById("text1").value;
    var str2 = document.getElementById("text2").value;

    var data = {id: 1, content: "this is html test <img src=\"a.png\"/> test\r\nhahaha"};
    window.WebViewJavascriptBridge.send(
        data,
        function(responseData) {
            document.getElementById("show").innerHTML = "responseData from java, data = " + responseData;

        }
    );
}

function testClick1() {
    var str1 = document.getElementById("text1").value;
    var str2 = document.getElementById("text2").value;

    window.WebViewJavascriptBridge.callHandler(
        'submitFromWeb',
        {'param': str1},
        function(responseData) {
            document.getElementById("show").innerHTML = "responseData from java, data = " + responseData;
        }
    );
}

function bridgeLog(logContent) {
    document.getElementById("show").innerHTML = logContent;
}

function connectWebViewJavascriptBridge(callback) {
    if(window.WebViewJavascriptBridge) {
        callback(WebViewJavascriptBridge);
    }else {
        document.addEventListener(
            'WebViewJavascriptBridgeReady',
            function () {
                callback(WebViewJavascriptBridge);
            },
            false
        );
    }
}

connectWebViewJavascriptBridge(function(bridge) {
    bridge.init(function (message, responseCallback) {
        console.log('JS got a message', message);
        var data = {
            'JavaScript Responds': 'Wee'
        };
        console.log('JS responding with', data);
        responseCallback(data);
    });

    bridge.registerHandler("functionInJs", function(data, responseCallback) {
        document.getElementById("show").innerHTML = ("data from java: = " + data);
        var responseData = "Javascript Says Right back aka!";
        responseCallback(responseData);
    });
});