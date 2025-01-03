
console.log('1');

//检验用户名
function checkUsername(username){
    if(username === null || username ==='' ||username === 0 ) {
        return '用户名不能为空';
    }
    if(username.length < 6) {
        return '用户名长度必须大于5';

    }
    return '';
}

//检验密码
function checkPassword(password){
    if(password === null || password ==='' || password === 0 ) {
        return '密码不能为空';
    }
    if(password.length < 6) {
        return '密码长度必须大于5';
    }

    // 校验密码是否包含字母和数字
    var hasLetter = /[a-zA-Z]/.test(password);  // 检查是否包含字母
    var hasDigit = /\d/.test(password);  // 检查是否包含数字

    if (!(hasLetter && hasDigit)) {
        return '密码必须包含字母和数字';
    }

    return '';
}


$(function(){
    //用户名的判断
    $('#username').on('blur', function(){
        var username = $(this).val();
        var validateMsg = checkUsername(username);
        if(validateMsg === '') {
            $('#username-feedback').text('');
            $.ajax({
                type    : 'GET',
                url     : '/GameShop/usernameIsExist?username='+username ,
                success : function(data){
                    if(data === 'Exist'){
                        $('#username-feedback').text('用户名已被注册');
                    }else {
                        $('#username-feedback').text('');
                    }
                } ,
                error   : function(){
                    console.log(errorMsg);
                }
            });
        }else{
            $('#username-feedback').text(validateMsg);
        }
    })

    //密码的判定
    $('#password').on('blur', function(){
        var password = $(this).val();
        var validateMsg = checkPassword(password);
        $('#password-feedback').text('');
        if(validateMsg === '') {
            $('#password-feedback').text('');
        } else{
            $('#password-feedback').text(validateMsg);
        }
    })

    // 两次输入的密码是否一致
    $('#repeatPassword').on('blur', function(){
        var repeatPassword = $(this).val();
        var password = $('#password').val();
        $('#repeatPassword-feedback').text('');
        if(repeatPassword!== password) {
            $('#repeatPassword-feedback').text('两次输入的密码不一致');
        } else {
            $('#repeatPassword-feedback').text('');
        }
    })

    //邮箱判定
    $('#email').on('blur', function() {
        var email = $(this).val();  // 获取邮箱输入框的值
        var emailFeedback = $('#email-feedback');  // 显示邮箱错误信息的地方

        // 清除之前的错误信息
        emailFeedback.text('');

        // 校验邮箱是否为空
        if (email === '') {
            emailFeedback.text('邮箱不能为空');
            return;  // 邮箱不能为空，停止进一步执行
        }

        // 校验邮箱格式是否正确
        var emailRegex = /^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zAZ]{2,}$/;

        if (!emailRegex.test(email)) {
            emailFeedback.text('请输入有效的邮箱地址');
            return;
        }
    })
    // 表单提交前的验证

    //// 注册成功后弹窗前往登录界面
    // $('.user-form-submit').on('click', function(){
    //     $.ajax({
    //         url: '/GameShop/register?' ,  // 注册接口
    //         type: 'POST',
    //         success: function(data) {
    //             console.log(data);
    //             if (data === 'Exist') {
    //
    //                 alert("您已完成注册，请前往登录");  // 显示提示信息
    //                 window.location.href = "/GameShop/loginForm";  // 假设登录页面的路径是 /login
    //             } else {
    //                 alert("注册失败，请重试");
    //                 window.location.href = "/GameShop/registerForm";
    //             }
    //         },
    //         error: function(xhr, status, error) {
    //             console.log('请求失败：', error);
    //         }
    //     });
    // })


});
