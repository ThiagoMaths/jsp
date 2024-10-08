<%@ page contentType="text/html; charset=ISO-8859-1"
         pageEncoding="ISO-8859-1"%>
    
<!DOCTYPE html>
<html lang="en">

<jsp:include page="head.jsp"/>

  <body>
  <jsp:include page="theme-loader.jsp"/>
  
  <div id="pcoded" class="pcoded">
      <div class="pcoded-overlay-box"></div>
      <div class="pcoded-container navbar-wrapper">
       
<jsp:include page="navbar.jsp"/>

          <div class="pcoded-main-container">
              <div class="pcoded-wrapper">
               
               <jsp:include page="navbarmainmenu.jsp"/>
               
                  <div class="pcoded-content">

                      <jsp:include page="page-header.jsp"/>
                      
                        <div class="pcoded-inner-content">
                            <div class="main-body">
                                <div class="page-wrapper">
                                    <div class="page-body">
                                        <div class="row">
                                           
                                           		<h1>Conteúdo das páginas do sistema</h1>
                                           
                                           
                                            </div>
                                        </div>
                                    </div>
                                </div>
                                <div id="styleSelector"> </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    
   <jsp:include page="javascriptfile.jsp"></jsp:include>
   

</body>

</html>
    