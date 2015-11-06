//1. visit https://theproject.zone/student/submissions/3/57 
//2. Modify DNS and QUERY before use
//3. Paste script to your Chrome/Firefox web console to submit query to TPZ automatically. Hit enter.

var DNS = 'ec2-54-174-97-35.compute-1.amazonaws.com'		//The web server's DNS
var QUERY_LIST = ['Q2-MySQL', 'Q2-HBase', 'Q3-MySQL', 'Q3-HBase', 'Q4-MySQL', 'Q4-HBase'] //Do not modify this 
var QUERY = QUERY_LIST[1];		//Which query to test
var DURATION = '60';			//Duration in seconds


var FORM_URL = '/twitter/submit/';
var POST = 'POST';
var TASK = '53';	//Task: Development 
var PROJECT = 'p619-phase2';
var INTERVAL = '70000';

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

