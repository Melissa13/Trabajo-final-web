<#include "base.ftl">

<#macro page_head>
    <title>Login</title>
</#macro>

<#macro page_body>
    <div class="container">
        <form th:action="@{/login}" method="POST" class="form-signin">
            <h3 class="form-signin-heading" th:text="Welcome"></h3>
            <br/>

            <input type="text" id="username" name="username"  th:placeholder="usuario"
                   class="form-control" /> <br/>
            <input type="password"  th:placeholder="Password"
                   id="password" name="password" class="form-control" /> <br />

            <div align="center" th:if="${param.error}">
                <p style="font-size: 20; color: #FF1C19;">Email or Password invalid, please verify</p>
            </div>
            <button class="btn btn-lg btn-primary btn-block" name="Submit" value="Login" type="Submit" th:text="Login"></button>
        </form>
    </div>

    <form th:action="@{/registro}" method="get">
        <button class="btn btn-md btn-warning btn-block" type="Submit">Go To Registration Page</button>
    </form>
</#macro>