function Reload(url){
	if(url) window.location.href = url;
	else window.location.reload();
}
function GetUrlParts(url) {
    var a = document.createElement('a');
    a.href = url;
    return {
        href: a.href,
        host: a.host,
        hostname: a.hostname,
        port: a.port,
        pathname: a.pathname,
        protocol: a.protocol,
        hash: a.hash,
        search: a.search
    };
}
function GetCallback(callback){
	return function(data){
		if(CheckSession(data)){
			Reload();
			return;
		}
		if(callback)
			callback(data);
	}
}
function CheckSession(data){
	if(data && (typeof data) =="string"){
		if(data == "session.expired")
			return true;
	}
	return false;
}
function GetSha1(str){
	if(null==str)
		str=""
	var sha1 = new jsSHA("SHA-1", "TEXT")
		sha1.update(str)
		return sha1.getHash("HEX")

}

var CookieUtil = {
	// 设置cookie
	SetCookie : function(name, value, expires) {
		var future = new Date(new Date().getTime() + expires * 1000);
		document.cookie = name + "=" + value + "; expires="
				+ future.toUTCString();// console.log(document.cookie)// path=/"
	},
	// 获取cookie
	GetCookie : function(name) {
		var arr = document.cookie.split("; "); // 注意是分号加一个空格
		for ( var i in arr) {
			var arr2 = arr[i].split("=");
			if (arr2[0] == name) {
				return arr2[1];
			}
		}
		return "";
	},
	// 删除cookie
	RemoveCookie : function(name) {
		this.SetCookie(name, "1", -1); // 过期时间设置为-1，意思就是昨天就过期了
	}
}

Date.prototype.simple = function() {
	return this.toLocaleString()
}

//倒计时
var CountDown = function(num,callback){
	var s = num + 1;
	function reduce(){
		s = s - 1;
		setTimeout(function(){
			if(s>0){
				reduce();
			}else{
				callback();
			}
		},1000);
	}
	return reduce();
}