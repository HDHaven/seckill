// 存放主要交互逻辑js代码
// JavaScript模块化
var seckill = {
		// 封装秒杀相关ajax的url
		URL : {
			now : function() {
				return "/seckill/seckill/time/now";
			},
			exposer : function(seckillId) {
				return "/seckill/seckill/"+ seckillId + "/exposer";
			},
			execution : function(seckillId, md5) {
				return "/seckill/seckill/"+ seckillId +"/"+ md5 +"/execution";
			}
		},
		// 处理秒杀交互逻辑
		handlerSeckill : function(seckillId, node) {
			node.hide().html('<button class="btn btn-primary btn-lg" id="killBtn">开始秒杀</button>');
			$.post(seckill.URL.exposer(seckillId), {}, function(result){
				// 在回调函数中执行交互流程
				if(result && result['success']) {
					var exposer = result['data'];
					if(exposer['exposed']) {
						// 秒杀启动，获取秒杀地址
						var md5 = exposer['md5'];
						var killUrl = seckill.URL.execution(seckillId, md5);
						// 绑定一次点击事件
						$('#killBtn').one('click', function(){
							// 执行秒杀
							$(this).addClass('disabled');
							$.post(killUrl, {}, function(result) {
								if(result && result['success']) {
									var killResult = result['data'];
									var state = killResult['state'];
									var stateInfo = killResult['stateInfo'];
									node.html('<span class="label label-success">'+ stateInfo +'</span>')
								}
							});
						});
						node.show();
					} else {
						// 秒杀未启动，重新计时
						var now = exposer['now'];
						var start = exposer['start'];
						var end = exposer['end'];
						countdown(seckillId, now, start, end);
					}
				}
			});
		},
		// 计时交互
		countdown : function(seckillId, nowTime, startTime, endTime) {
			var seckillBox = $('#seckill-box');
			if(nowTime > endTime) {
				// 秒杀已经结束
				seckillBox.html('秒杀结束！');
			} else if(nowTime < startTime) {
				// 秒杀未开启，计时事件绑定
				var killTime = new Date(startTime + 1000);
				seckillBox.countdown(killTime, function(event) {
					// 时间格式
					var format = event.strftime('秒杀倒计时：%D天 %H时 %M分 %S秒');
					seckillBox.html(format);
					/*时间完成后事件*/
				}).on('finish.countdown', function(){
					// 获取秒杀地址，控制显示逻辑，执行秒杀
					seckill.handlerSeckill(seckillId, seckillBox);
				});
			} else {
				// 进行秒杀
				seckill.handlerSeckill(seckillId, seckillBox);
			}
		},
		// 手机号码验证
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
				// 手机验证和登录
				// 在cookie中 查找手机号
				var killPhone = $.cookie('killPhone');
				//验证手机号
				if(!seckill.validatePhone(killPhone)) {
					// 绑定phone，控制输出
					var killPhoneModal = $('#killPhoneModal');
					killPhoneModal.modal({
						show : true,// 显示弹出层
						backdrop : 'static',// 禁止位置关闭
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
					});
				}
				// 已经登录
				// 计时交互
				var seckillId = params['seckillId'];
				var startTime = params['startTime'];
				var endTime = params['endTime'];
				$.get(seckill.URL.now(), {}, function(result){
					if(result && result['success']) {
						var nowTime = result['data'];
						// 时间判断
						seckill.countdown(seckillId, nowTime, startTime, endTime);
					} else {
						console.log('result:'+ result);
					}
				});
			}
		}
};
