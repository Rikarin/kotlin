package net.rikarin.http

import java.io.OutputStream

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