var LoadingUtil = function(){};

LoadingUtil.prototype = {
	showLoading : function(){
		var length = AJS.$("body").find(".loading").length;
		if(length == 0){
			var html = '<div class="loading"><div class="loader"></div></div>';
			AJS.$("body").append(html);
		}
	},
	hideLoading : function(){
		AJS.$("body").find(".loading").remove();
	}
}

var loadingUtil = new LoadingUtil();