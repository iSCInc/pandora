<#-- @ftlvariable name="" type="java.util.HashMap<String, String>" -->
<html>
<head>
    <title>Forum Resource Documentation</title>
</head>
<body>
<h1 class="page-header">forums
    <small>relation</small>
</h1>
<small>
    <div>
        <h2>GET</h2>
        <p class="description">Show a list of forums for a given site</p>
        <div class="response">
            <h3>Responses</h3>
            <div class="code">
                <h4>200 OK</h4>
                <div class="body">
                    <h5>Body</h5>
                    <div class="links">
                        <h6>Links</h6>
                        <ul>
                            <li><a href="${relMap['forums']?html}">doc:forums</a> - forums for this site</li>
                        </ul>
                    </div>
                    <div class="embedded">
                        <h6>Embedded Resources</h6>
                        <p>Each <a href="${relMap['forums']?html}">doc:forum</a> is embedded in this resource via a
                            relation of the same name. For example:</p>
                        <pre>
                            "_embedded": {
                                "doc:forum": [{
                                    ... individual forum resource ...
                                }, { 
                                    ... individual forum resource ...
                                }]
                            }
                        </pre>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <div>
        <h2>GET</h2>
        <p class="description">Details of a specific forum for a given site</p>
        <div class="response">
            <h3>Responses</h3>
            <div class="code">
                <h4>200 OK</h4>
                <div class="body">
                    <h5>Body</h5>
                    <div class="links">
                        <h6>Links</h6>
                        <ul>
                            <li><a href="${relMap['threads']?html}">doc:threads</a> - threads in the forum for this site</li>
                        </ul>
                    </div>
                    <div class="embedded">
                        <h6>Embedded Resources</h6>
                        <p>Each <a href="${relMap['forums']?html}">doc:forum</a> is embedded in this resource via a
                            relation of the same name. For example:</p>
                        <pre>
                            "_embedded": {
                                "doc:forum": [{
                                    ... individual forum resource ...
                                }, {
                                    ... individual forum resource ...
                                }]
                            }
                        </pre>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <div>
        <h2>POST</h2>
        <p class="description">Create a new forum for the site</p>
        <div class="request">
            <h3>Request</h3>
            <div class="headers">
                <h4>Headers</h4>
                <div class="type">
                    The request should have the Content-Type application/json
                </div>
            </div>
            <div class="body">
                <h4>Body</h4>
                <div class="required">
                    <h5>Required properties</h5>
                    <ul>
                        <li><strong>parentId</strong> : parent ID of the forum</li>
                        <li><strong>name</strong> : name of the forum</li>
                    </ul>
                    <h5>Example</h5>
                    <pre>
                        {
                            "parentId": 1,
                            "name": "First Forum",
                        }
                    </pre>
                </div>
            </div>
        </div>

        <div class="response">
            <h3>Responses</h3>
            <div class="code">
                <h4>201 Created</h4>
                <div class="headers">
                    <h5>Headers</h5>
                    <ul>
                        <li>Location: URI of the created <a href="${relMap['forums']?html}">forums</a></li>
                    </ul>
                </div>
            </div>
            <div class="code">
                <h4>401 Unauthorized</h4>
                <div class="headers">
                    <h5>Headers</h5>
                    <ul>
                        <li>WWW-Authenticate: indicates the Auth method (typically HTTP Basic)</li>
                    </ul>
                </div>
            </div>
        </div>
    </div>
    <div>
        <h2>PUT</h2>
        <p class="description">Update a forum in the site</p>
        <div class="request">
            <h3>Request</h3>
            <div class="headers">
                <h4>Headers</h4>
                <div class="type">
                    The request should have the Content-Type application/json
                </div>
            </div>
            <div class="body">
                <h4>Body</h4>
                <div class="required">
                    <h5>Required properties</h5>
                    <ul>
                        <li><strong>name</strong> : updated name of the forum</li>
                    </ul>
                    <h5>Optional properties</h5>
                    <ul>
                        <li><strong>parentId</strong> : new parent ID of the forum</li>
                    </ul>
                    <h5>Example</h5>
                    <pre>
                        {
                            "parentId": 1,
                            "name": "First Forum",
                        }
                    </pre>
                </div>
            </div>
        </div>

        <div class="response">
            <h3>Responses</h3>
            <div class="code">
                <h4>204 No Content (if successful)</h4>
            </div>
            <div class="code">
                <h4>201 Created (if entity does not exist it is created)</h4>
                <div class="headers">
                    <h5>Headers</h5>
                    <ul>
                        <li>Location: URI of the created <a href="${relMap['forums']?html}">forums</a></li>
                    </ul>
                </div>
            </div>
            <div class="code">
                <h4>401 Unauthorized</h4>
                <div class="headers">
                    <h5>Headers</h5>
                    <ul>
                        <li>WWW-Authenticate: indicates the Auth method (typically HTTP Basic)</li>
                    </ul>
                </div>
            </div>
        </div>
    </div>
</small>
</body>
</html>