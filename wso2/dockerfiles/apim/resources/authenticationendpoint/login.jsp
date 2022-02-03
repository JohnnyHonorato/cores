<%
    String serviceProvider = request.getParameter("sp");
    if (serviceProvider.startsWith("virtus_way")) {
        RequestDispatcher dispatcher = request.getRequestDispatcher("virtus_way_login.jsp");
        dispatcher.forward(request, response);
    } else {
        RequestDispatcher dispatcher = request.getRequestDispatcher("default_login.jsp");
        dispatcher.forward(request, response);
    }
%>
