<%@ page contentType="text/html;charset=GB2312" language="java" %>
<html>
<head></head>

<body>
	<h1>登录</h1>

	<form name='f' action="dharma_login" method='POST'>
		<ul>
			<li>
				<label>用户名：<input type='text' name='username' value=''></label>
			</li>
			<li>
				<label>密码：<input type='password' name='password' /></label>
			</li>
			<li>
				<label>记住我 <input name="remember-me" type="checkbox" /></label>
			</li>
			<li>
				<input name="submit" type="submit" value="登录" />
			</li>
		</ul>
	</form>

</body>
</html>