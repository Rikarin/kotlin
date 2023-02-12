package net.rikarin.http

import java.io.InputStream

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