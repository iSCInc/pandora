<html>
<head>
    <title>Thread Resource Documentation</title>
</head>
<body>
<h1 class="page-header">threads
    <small>relation</small>
</h1>
<small>
    <div>
        <h2>GET</h2>
        <p class="description">Show a list of threads for a given forum on a site</p>
        <div class="response">
            <h3>Responses</h3>
            <div class="code">
                <h4>200 OK</h4>
                <div class="body">
                    <h5>Body</h5>
                    <div class="links">
                        <h6>Links</h6>
                        <ul>
                            <li><a href="${relMap['threads']?html}">doc:threads</a> - threads within the forum</li>
                            <li>next pagination (OPTIONAL)</li>
                            <li>previous pagination (OPTIONAL)</li>
                            <li>first pagination (OPTIONAL)</li>
                            <li>last pagination (OPTIONAL)</li>
                        </ul>
                        <div class="required">
                            <h6>Required Path Parameters</h6>
                            <ul>
                                <li><strong>siteId (integer)</strong>: id of the site</li>
                                <li><strong>threadId (integer)</strong>: id of the thread</li>
                            </ul>
                            <h5>Example</h5>
                                <pre>/{siteId}/threads/{threadId}</pre>
                        </div>
                        <h6>Optional Query Parameters</h6>
                        <ul>
                            <li><strong>offset (integer)</strong>: offset of the pagination</li>
                            <li><strong>limit (integer)</strong>: number of threads per pagination</li>
                            <li><strong>responseGroup (string)</strong>: 
                                <strong>"small"</strong>(default) for shallow representation |
                                <strong>"full"</strong> for deep representation
                            </li>
                        </ul>
                    </div>
                    <div class="embedded">
                        <h6>Embedded Resources</h6>
                        <p>Each <a href="${relMap['posts']?html}">doc:posts</a> is embedded in this resource via a
                            relation of the same name. For example:</p>
                        <pre>
"_embedded":
{ "doc:posts":
    [{
        ... individual post resource ...}, {
        ... individual post resource ...
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
        <p class="description">Start a new Thread on the Forum for the Site</p>
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
                    <h6>Required Path Parameters</h6>
                    <ul>
                        <li><strong>siteId (integer)</strong>: id of the site</li>
                        <li><strong>forumId (integer)</strong>: id of the forum to add the thread</li>
                    </ul>
                    <h5>Example</h5>
                    <pre>/{siteId}/forums/{forumId}/threads</pre>
                </div>
                <div class="required">
                    <h5>Required properties</h5>
                    <ul>
                        <li><strong>title</strong> : the title of the thread</li>
                        <li><strong>userId</strong> : id of the user starting the thread</li>
                        <li><strong>body</strong> : body of the first post</li>
                    </ul>
                    <h5>Example</h5>
                    <pre>
{
    "title": "First Thread in the Forum",
    "userId": 1,
    "body": "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed "
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
                        <li>Location: URI of the created <a href="${relMap['threads']?html}">thread</a></li>
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
            <div class="code">
                <h4>400 Bad Request</h4>
            </div>
        </div>
    </div>
    <div>
        <h2>PUT</h2>
        <p class="description">Update a thread on  in the site</p>
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
                        <li>Location: URI of the created <a href="/rels/forums">forums</a></li>
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
        <h2>DELETE</h2>
        <p class="description">Delete the thread in the forum for the site</p>
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
                        <li><strong>siteId</strong> : the site id </li>
                        <li><strong>threadId</strong> : the thread id to be deleted</li>
                    </ul>
                    <h5>Example</h5>
                    <pre>/{siteId}/threads/{threadId}</pre>
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
                        <li>Location: URI of the created <a href="/rels/forums">forums</a></li>
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