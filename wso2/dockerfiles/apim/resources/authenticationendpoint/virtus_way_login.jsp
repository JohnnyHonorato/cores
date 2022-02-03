<%--
  ~ Copyright (c) 2018, WSO2 Inc. (http://www.wso2.org) All Rights Reserved.
  ~
  ~ WSO2 Inc. licenses this file to you under the Apache License,
  ~ Version 2.0 (the "License"); you may not use this file except
  ~ in compliance with the License.
  ~ You may obtain a copy of the License at
  ~
  ~  http://www.apache.org/licenses/LICENSE-2.0
  ~
  ~ Unless required by applicable law or agreed to in writing,
  ~ software distributed under the License is distributed on an
  ~ "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
  ~ KIND, either express or implied.  See the License for the
  ~ specific language governing permissions and limitations
  ~ under the License.
  --%>
<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="org.apache.commons.lang.StringUtils" %>

<%
    final String SESSION_DATA_KEY_PARAM = "sessionDataKey";
    final String AUTH_FAILURE_PARAM = "authFailure";
    final String AUTH_FAILURE_MSG_PARAM = "authFailureMsg";
    String errorMessage = "Authentication Failed! Please Retry";
    String loginFailed = "false";
    String DOMAIN_NAME=System.getenv("DOMAIN_NAME");
    String formActionURL = "https://" + DOMAIN_NAME + "/commonauth";
    
    if (StringUtils.isNotEmpty(request.getParameter(AUTH_FAILURE_PARAM)) &&
            "true".equals(request.getParameter(AUTH_FAILURE_PARAM))) {
        loginFailed = "true";
        if (request.getParameter(AUTH_FAILURE_MSG_PARAM) != null) {
            errorMessage = request.getParameter(AUTH_FAILURE_MSG_PARAM);
            
            if (errorMessage.equalsIgnoreCase("login.fail.message")) {
                errorMessage = "Authentication Failed! Please Retry.";
            }
        }
    }
%>

<html>
    <head>
        <meta charset="utf-8">
        <script src="libs/jquery_3.4.1/jquery-3.4.1.js"></script>
        <link href="https://fonts.googleapis.com/css?family=Roboto" rel="stylesheet">
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" integrity="sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u" crossorigin="anonymous">
        <link rel="stylesheet" href="css/style.css">
        <link rel="stylesheet" href="css/login.css">
        <title>Login Portal</title>
    </head>

    <body style="font-family: 'Roboto', sans-serif;"> 
        <div class="page">
            <div class="container">
                <div class="row">
                    <div class="col mx-auto">
        
                        <div class="row justify-content-center">
                            <div class="col-lg-4 col-xl-4 col-md-6 d-block mx-auto">
                                <div class="text-center mb-6">
                                    <img src="images/logo-large.png" class="logo" alt="">
                                </div>
                                <div class="card-group mb-0">
                                    <div class="card p-4">
                                        <form id="loginForm" class="card-body" class="login-form" action='<%=formActionURL%>' method="post">
                                            <div class="form-group">
                                                <label>Usuário/Email</label>
                                                <input type="text" class="form-control" placeholder="Usuário/Email" id='username' name="username" required>
                                            </div>
                                            <div class="form-group">
                                                <label>Senha</label>
                                                <input type="password" class="form-control password" placeholder="Senha" id='password' name="password" required>
                                            </div>
                                            <div>
                                                <input type="hidden" name="sessionDataKey"
                                                       value='<%=request.getParameter(SESSION_DATA_KEY_PARAM)%>'/>
                                            </div>
        
                                            <button type="submit" class="btn btn-primary btn-block">
                                                Entrar
                                            </button>
                                        </form>
                                        <% if (loginFailed.equals("true")) { %>
                                        <div style="color: red; padding-top: 40px;">
                                            <%=errorMessage%>
                                        </div>
                                        <% } %>
                                    </div>
                                </div>
        
                                <a class="bottom-link" translate>Esqueceu sua Senha?</a>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
	    <script>
	        function checkIfIsLogged() {
                var isLogged = document.cookie.indexOf('opbs=') != -1;
                if (isLogged) {
                    var url = new URL(window.location.href);
                    var redirectUrl = url.searchParams.get("redirect_uri");
                    window.location.assign(redirectUrl);
                }
            }

            $(document).ready(function(){
                $.fn.validateBlankUsername = function() {
                    $(this).on('submit',function(e) {
                        var $form = $(this);
                        var username = $form.find('input[name="username"]').val();
                        if (username.match(/^ *$/) !== null) {
                                e.preventDefault();
                                var href = window.location.href + "&authFailure=true&authFailureMsg=login.fail.message";
                                window.location.replace(href);
                        }
                    });
        
                    return this;
                };
                $('#loginForm').validateBlankUsername();
            });
        </script>
    </body>
</html>