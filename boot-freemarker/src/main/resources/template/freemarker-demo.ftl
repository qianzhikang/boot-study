<!doctype html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>freemarker测试</title>
    <link rel="stylesheet" href="/webjars/bootstrap/css/bootstrap.css">
</head>
<body>

<div class="container">
    <h1>${title}</h1>
    <table class="table table-bordered table-striped table-hover">
        <tr>
            <th>name</th>
            <th>age</th>
            <th>address</th>
        </tr>

        <#list personList as person>
            <tr>
                <td>${person.name}</td>
                <td>${person.age}</td>
                <#if !person.address??>
                    <td>没有填写地址！</td>
                <#else>
                    <td>${person.address}</td>
                </#if>
            </tr>
        </#list>
    </table>

    <#assign flag=2>
    <#if flag == 1>
        <h2 class="text-primary">南京工业职业技术大学</h2>
    </#if>
    <#if flag == 2>
        <h2 class="text-danger">消失了!!!</h2>
    </#if>
</div>
<script src="/webjars/jquery/jquery.min.js"></script>
<script src="/webjars/bootstrap/js/bootstrap.min.js"></script>
</body>
</html>