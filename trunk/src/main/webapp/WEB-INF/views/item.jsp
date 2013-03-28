<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="pageTitle" scope="request" value="Mange Items"/>
<%@ include file="header.jsp" %>

		<script type="text/javascript" src="<c:url value='/scripts/lib/RowSelector.js' />"></script>
		<script type="text/javascript" src="<c:url value='/scripts/item.js' />"></script>

		<h1>Manage Items</h1>

		<form:form class="form-horizontal" name="addItem" action="" method="post" modelAttribute="itemCommand" onsubmit="return validateForm()">

            <legend>New Item</legend>

            <div class="well">
                <div class="control-group">
                    <form:label class="control-label" for="name" path="name">Name</form:label>
                    <div class="controls">
                        <form:input path="name" />
                        <form:errors path="name" cssClass="text-error" />
                    </div>
                </div>

                <div class="control-group">
                    <form:label class="control-label" for="price" path="price">Price</form:label>
                    <div class="controls">
                        <form:input path="price" />
                        <form:errors path="price" cssClass="text-error" />
                    </div>
                </div>

                <div class="control-group">
                    <form:label class="control-label" for="type" path="type">Type</form:label>
                    <div class="controls">
                        <form:select path="type">
                            <form:option value="" label="Select" />
                            <form:options items="${itemTypes}"/>
                        </form:select>
                        <form:errors path="type" cssClass="text-error" />
                    </div>
                </div>

                <div class="control-group">
                    <form:label class="control-label" for="desription" path="description">Description</form:label>
                    <div class="controls">
                        <form:textarea path="description" />
                        <form:errors path="description" cssClass="text-error" />
                    </div>
                </div>

                <div class="control-group">
                    <form:label class="control-label" for="quantity" path="quantity">Quantity</form:label>
                    <div class="controls">
                        <form:input path="quantity" />
                        <form:errors path="quantity" class="text-error" />
                    </div>
                </div>

                <div class="control-group">
                    <div class="controls">
                        <button type="submit" value="Create new item" id="createItem" class="btn btn-warning">Create Item</button>
                    </div>
                </div>

                </div>
		</form:form>

		<form:form action="" method="post" modelAttribute="itemGrid">

            <legend>Update Items</legend>

            <div class="well">
			    <table class="table">
				<thead><tr><th><input type="checkbox" class="toggleAll" /></th>
				<th>Name</th>
				<th>Price</th>
				<th>Description</th>
				<th>ItemType</th>
				<th>Quantity</th>
				</tr></thead>
				<tbody>
					<c:forEach var="itemEntry" items="${itemGrid.itemMap}" varStatus="row">
						<tr>
							<td>
								<form:input type="hidden" disabled="${!itemEntry.value.selected}" path="itemMap[${itemEntry.key}].itemId" />
								<c:if test="${itemMap[itemEntry.key].selected}">
									<input type="checkbox" checked="checked" class="rowSelector" />
								</c:if>
								<c:if test="${!itemMap[itemEntry.key].selected}">
									<input type="checkbox" class="rowSelector" />
								</c:if>
							</td>
							<td>
								<form:errors path="itemMap[${itemEntry.key}].name" cssClass="text-error" />
								<form:input disabled="${!itemEntry.value.selected}" path="itemMap[${itemEntry.key}].name" />
							</td>
							<td>
								<form:errors path="itemMap[${itemEntry.key}].price" cssClass="text-error" />
								<form:input class="input-small" disabled="${!itemEntry.value.selected}" path="itemMap[${itemEntry.key}].price" />
							</td>
							<td>
								<form:errors path="itemMap[${itemEntry.key}].description" cssClass="text-error" />
								<form:input disabled="${!itemEntry.value.selected}" path="itemMap[${itemEntry.key}].description" />
							</td>
							<td>
                            	<form:errors path="itemMap[${itemEntry.key}].type" cssClass="text-error" />
       							<form:input disabled="${!itemEntry.value.selected}" path="itemMap[${itemEntry.key}].type" />
     					    </td>
     					    <td>
                                <form:errors path="itemMap[${itemEntry.key}].quantity" cssClass="text-error" />
                                <form:input class="input-small" disabled="${!itemEntry.value.selected}" path="itemMap[${itemEntry.key}].quantity" />
                            </td>
						</tr>
					</c:forEach>
				</tbody>
			    </table>

			<p>
                <button type="submit" value="Update all enabled items" name="update" class="btn btn-warning">Update all enabled items</button>
                <button type="submit" value="Delete all enabled items" name="delete" class="btn btn-danger">Delete all enabled items</button>
			</p>

            </div>
		</form:form>
<%@ include file="footer.jsp" %>
