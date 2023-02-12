package net.rikarin.http.internal

import net.rikarin.dependencyInjeciton.ServiceProvider
import net.rikarin.dependencyInjeciton.ServiceScopeFactory
import net.rikarin.dependencyInjeciton.getRequiredService
import net.rikarin.dependencyInjeciton.getService
import net.rikarin.http.HttpContext
import net.rikarin.http.HttpContextAccessor
import net.rikarin.http.HttpContextFactory
import net.rikarin.http.features.FeatureCollection

internal class DefaultHttpContextFactory(serviceProvider: ServiceProvider) : HttpContextFactory {
    internal val httpContextAccessor: HttpContextAccessor?
    // TODO: FormOptions
    private val _serviceScopeFactory: ServiceScopeFactory

    init {
        httpContextAccessor = serviceProvider.getService<HttpContextAccessor>() // can be null
//        _formOptions = serviceProvider.GetRequiredService<IOptions<FormOptions>>().Value;
        _serviceScopeFactory = serviceProvider.getRequiredService<ServiceScopeFactory>()
    }

    override fun create(featureCollection: FeatureCollection): HttpContext {
        val httpContext = DefaultHttpContext(featureCollection)
        initialize(httpContext, featureCollection)
        return httpContext
    }

    override fun dispose(httpContext: HttpContext) {
        if (httpContextAccessor != null) {
            httpContextAccessor.httpContext = null
        }
    }

    private fun initialize(httpContext: DefaultHttpContext, featureCollection: FeatureCollection) {
        //http context iniialize

        if (httpContextAccessor != null) {
            httpContextAccessor.httpContext = httpContext
        }

//        httpContext.FormOptions = _formOptions;
//        httpContext.serviceScopeFactory = _serviceScopeFactory;
    }


//
//    internal void Dispose(DefaultHttpContext httpContext)
//    {
//        if (_httpContextAccessor != null)
//        {
//            _httpContextAccessor.HttpContext = null;
//        }
//
//        httpContext.Uninitialize();
//    }
}