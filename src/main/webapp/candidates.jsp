<%--
  Created by IntelliJ IDEA.
  User: Evgenii
  Date: 23.06.2021
  Time: 19:01
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import="ru.job4j.dream.store.Store" %>
<%@ page import="ru.job4j.dream.model.Candidate" %>
<%@ page import="java.util.Collection" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page isELIgnored="false" %>
<!doctype html>
<html lang="en">
<head>
  <!-- Required meta tags -->
  <meta charset="utf-8">
  <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">

  <!-- Bootstrap CSS -->
  <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css"
        integrity="sha384-Vkoo8x4CGsO3+Hhxv8T/Q5PaXtkKtu6ug5TOeNV6gBiFeWPGFN9MuhOf23Q9Ifjh" crossorigin="anonymous">
  <script src="https://code.jquery.com/jquery-3.4.1.slim.min.js"
          integrity="sha384-J6qa4849blE2+poT4WnyKhv5vZF5SrPo0iEjwBvKU7imGFAV0wwj1yYfoRSJoZ+n" crossorigin="anonymous"></script>
  <script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.0/dist/umd/popper.min.js"
          integrity="sha384-Q6E9RHvbIyZFJoft+2mJbHaEWldlvI9IOYy5n3zV9zzTtmI3UksdQRVvoxMfooAo" crossorigin="anonymous"></script>
  <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/js/bootstrap.min.js"
          integrity="sha384-wfSDF2E50Y2D1uUdj0O3uMBJnjuUD4Ih7YwaYd1iqfktj0Uod8GCExl3Og8ifwB6" crossorigin="anonymous"></script>

  <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
  <title>Работа мечты</title>
</head>
<body>
<div class="container pt-3">

  <div class="row">
    <div class="card" style="width: 100%">
      <div class="card-header">
        Кандидаты
      </div>
      <div class="card-body">
        <table class="table">
          <thead>
          <tr>
            <th scope="col">Имя кандидата</th>
            <th scope="col">Действия c кандидатом</th>
            <th scope="col">Действия с фото кандидата</th>
          </tr>
          </thead>
          <tbody>
          <c:forEach items="${candidates}" var="can">
            <tr>

              <td>
                <!-- Это имя кандидата -->
                <c:out value="${can.name}"/>
              </td>

              <!-- Это  для редактирования пользователя-->
              <td>
                <a href='<c:url value="/editCandidate.jsp?id=${can.id}"/>'>
                  <i> Редактировать </i>
                </a>
                <!-- Это  для удаления пользователя-->
                <a href='<c:url value="/delete.jsp?id=${can.id}"/>'>
                  <i> Удалить </i>
                </a>
              </td>

              <td>
                <!-- Это  для редактирования фото-->
                <a href='<c:url value="/upload?id=${can.id}"/>'>
                  <i>Загрузка Удаление Скачивание фото</i>
                </a>
              </td>

            </tr>
          </c:forEach>
          </tbody>
        </table>
      </div>
    </div>
  </div>
</div>
</body>
</html>
