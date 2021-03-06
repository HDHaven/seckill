// 存放主要交换逻辑js代码
// JavaScript模块化
alert('进入seckill.js');
var seckill = {
		// 封装秒杀相关ajax的url
		URL : {
			
		},
		validatePhone : function(phone) {
			if(phone && phone.length == 11 && !isNaN(phone)) {
				return true;
			} else {
				return false;
			}
		},
		// 详情页秒杀逻辑
		detail : {
			// 详情页初始化
			init : function(params) {
				// 手机验证和登录，计时交互
				// 在cookie中 查找手机号
				var killPhone = $.cookie('killPhone');
				var seckillId = params['seckillId'];
				var startTime = params['startTime'];
				var endTime = params['endTime'];
				//验证手机号
				if(!seckill.validatePhone(killPhone)) {
					// 绑定phone，控制输出
					var killPhoneModal = $('#killPhoneModal');
					killPhoneModal.modal({
						show : true,// 显示弹出层
						backdrop : static,// 禁止位置关闭
						keyboard : false// 关闭键盘事件
					});
					$('#killPhoneBtn').click(function(){
						var inputPhone = $('#killPhoneKey').val();
						if(seckill.validatePhone(inputPhone)) {
							// 电话写入cookie
							$.cookie('killPhone', inputPhone, {expires:7,path:'/seckill'});
							// 验证通过，刷新页面
							window.location.reload();
						} else {
							$('#killPhoneMessage').hide().html('<label class="label label-danger">手机号错误！</label>').show(300);
						}
					})
				}
				
			}
		}
};