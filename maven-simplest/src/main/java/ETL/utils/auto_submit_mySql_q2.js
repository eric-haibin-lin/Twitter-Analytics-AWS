//1. visit https://theproject.zone/student/submissions/3/57 
//2. Modify DNS and QUERY before use
//3. Paste script to your Chrome/Firefox web console to submit query to TPZ automatically. Hit enter.

var DNS = 'mysqlQ2-1928734176.us-east-1.elb.amazonaws.com'		//The web server's DNS
var QUERY_LIST = ['Q2-MySQL', 'Q2-HBase', 'Q3-MySQL', 'Q3-HBase', 'Q4-MySQL', 'Q4-HBase'] //Do not modify this 
var QUERY = QUERY_LIST[0];		//Which query to test
var DURATION = '600';			//Duration in seconds


var FORM_URL = '/twitter/submit/';
var POST = 'POST';
var PROJECT = 'p619-phase2';
var TASK = '53';	//Task: Development 
var INTERVAL = '610000';

$('#submit').click(function()
{
    $.ajax({
        url: FORM_URL,
        type: POST,
        data:
        {
            task: TASK,
            course: 3,
            debug: 1,
            project: PROJECT,
            query: QUERY,
            duration: DURATION,
            dns: DNS,  
        },
        success: function(msg)
        {
            console.log("Request sent at " + new Date());
        }               
    });
    //Prevent page reload 
    return false;
});

function submitQuery(){
	$("#submit").click();		
};

submitQuery();
setInterval(submitQuery, INTERVAL);

