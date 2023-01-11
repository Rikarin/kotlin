package net.rikarin.http

import net.rikarin.dependencyInjeciton.ServiceProvider
import java.io.InputStream
import java.io.OutputStream

abstract class HttpContext {
//    public abstract IFeatureCollection Features { get; }
    abstract val request: HttpRequest
    abstract val response: HttpResponse
    abstract val connection: ConnectionInfo
//    public abstract WebSocketManager WebSockets { get; }
//    public abstract ClaimsPrincipal User { get; set; }
    abstract var items: Map<Any, Any?>
    abstract var requestServices: ServiceProvider
//    public abstract CancellationToken RequestAborted { get; set; }
    abstract var traceIdentifier: String
//    public abstract ISession Session { get; set; }
    abstract fun abort()
}

abstract class HttpRequest {
    abstract val httpContext: HttpContext
    abstract var method: String
    abstract var scheme: String
    abstract var isHttps: String
//    abstract host: HostString
//    public abstract PathString PathBase { get; set; }
//    public abstract PathString Path { get; set; }
//    public abstract QueryString QueryString { get; set; }
//    public abstract IQueryCollection Query { get; set; }
    abstract var protocol: String
    abstract val headers: HeaderDictionary
//    public abstract IRequestCookieCollection Cookies { get; set; }
    abstract var contentLength: Long?
    abstract var contentType: String?
    abstract var body: InputStream
//    public virtual PipeReader BodyReader { get => throw new NotImplementedException(); }
    abstract var hasFormContentType: Boolean
//    public abstract IFormCollection Form { get; set; }
//    public abstract Task<IFormCollection> ReadFormAsync(CancellationToken cancellationToken = new CancellationToken());
//    public virtual RouteValueDictionary RouteValues { get; set; } = null!;
}

abstract class HttpResponse {
    abstract val httpContext: HttpContext
    abstract var statusCode: Int
    abstract val headers: HeaderDictionary
    abstract var body: OutputStream
//    public virtual PipeWriter BodyWriter { get => throw new NotImplementedException(); }
    abstract var contentLength: Long?
    abstract var contentType: String?
//    public abstract IResponseCookies Cookies { get; }
    abstract val hasStarted: Boolean

//    public abstract void OnStarting(Func<object, Task> callback, object state);
//    public virtual void OnStarting(Func<Task> callback) => OnStarting(_callbackDelegate, callback);
//    public abstract void OnCompleted(Func<object, Task> callback, object state);
//    public virtual void RegisterForDispose(IDisposable disposable) => OnCompleted(_disposeDelegate, disposable);
//    public virtual void RegisterForDisposeAsync(IAsyncDisposable disposable) => OnCompleted(_disposeDelegate, disposable);
//    public virtual void OnCompleted(Func<Task> callback) => OnCompleted(_callbackDelegate, callback);
//    public virtual void Redirect([StringSyntax(StringSyntaxAttribute.Uri)] string location) => Redirect(location, permanent: false);
//    public abstract void Redirect([StringSyntax(StringSyntaxAttribute.Uri)] string location, bool permanent);

    abstract fun redirect(location: String, permanent: Boolean = false)
    abstract fun start()
    abstract fun complete()
}

