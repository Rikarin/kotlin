package net.rikarin.dependencyInjeciton

import kotlin.reflect.KClass
import kotlin.reflect.KMutableProperty
import kotlin.reflect.KType
import kotlin.reflect.full.hasAnnotation
import kotlin.reflect.full.memberProperties
import kotlin.reflect.full.primaryConstructor

class DefaultServiceProvider(
    private val implementations: Map<KType, List<ServiceDescriptor>>,
    private val singletons: MutableMap<ServiceDescriptor, Any>,
    serviceScope: ServiceScope?
) : ServiceProvider, ServiceScopeFactory {
    private val serviceScope: DefaultServiceScope

    init {
        if (serviceScope == null) {
            this.serviceScope = DefaultServiceScope(this)
        } else {
            this.serviceScope = serviceScope as DefaultServiceScope
        }
    }

    override fun getService(type: KType): Any? {
        if (type.classifier == ServiceProvider::class) {
            return this
        }

        if (implementations[type] == null) {
            return null
        }

        if (implementations[type]!!.size != 1) {
            throw Exception("multiple services found")
        }

        return getServices(type).first()
    }

    override fun getServices(type: KType): Collection<Any> {
        if (implementations[type] == null) {
            return emptyList()
        }

        val ret = mutableListOf<Any>()
        for (desc in implementations[type]!!) {
            // Implementation instance for singleton
            // check for already resolved types
            // resolve type
            // store type to cache

            if (desc.lifetime == ServiceLifetime.SINGLETON && desc.implementationInstance != null) {
                ret.add(desc.implementationInstance)
                continue
            }

            if (desc.lifetime == ServiceLifetime.SCOPED) {
                val scoped = serviceScope.scope[desc]
                if (scoped != null) {
                    ret.add(scoped)
                    continue
                }
            } else if (desc.lifetime == ServiceLifetime.SINGLETON) {
                val singleton = singletons[desc]
                if (singleton != null) {
                    ret.add(singleton)
                    continue
                }
            }

            var newInstance: Any
            if (desc.implementationType != null) {
                // TODO: this will use only primary constructor and not any other defined in the body of a class
                val ctr = (desc.implementationType.classifier as KClass<*>).primaryConstructor
                val params = ctr?.parameters!!

                val args = mutableListOf<Any>()
                for (p in params) {
                    args.add(getRequiredService(p.type))
                }

                newInstance = ctr.call(*args.toTypedArray())

                val props = newInstance::class.memberProperties.filter { it.hasAnnotation<Inject>() }
                for (prop in props) {
                    if (prop is KMutableProperty<*>) {
                        prop.setter.call(newInstance, getService(prop.returnType))
                    }
                }
            } else if (desc.implementationFactory != null) {
                newInstance = desc.implementationFactory.invoke(this)
            } else {
                throw Exception("broken ServiceDescriptor")
            }

            if (desc.lifetime == ServiceLifetime.SCOPED) {
                serviceScope.scope[desc] = newInstance
            } else if (desc.lifetime == ServiceLifetime.SINGLETON) {
                singletons[desc] = newInstance
            }

            ret.add(newInstance)
        }

        return ret.toList()
    }

    override fun getRequiredService(type: KType): Any {
        return getService(type) ?: throw Exception("service does not have any valid implementation for $type")
    }

    override fun createScope(): ServiceScope = DefaultServiceScope(this)
    override fun close() {}

    private class DefaultServiceScope(serviceProvider: DefaultServiceProvider) : ServiceScope {
        val scope: MutableMap<ServiceDescriptor, Any>

        override val serviceProvider: ServiceProvider

        init {
            if (serviceProvider.serviceScope != null) {
                scope = serviceProvider.serviceScope.scope.toMutableMap()
            } else {
                scope = mutableMapOf()
            }

            this.serviceProvider =
                DefaultServiceProvider(serviceProvider.implementations, serviceProvider.singletons, this)
        }

        override fun close() {}
    }
}