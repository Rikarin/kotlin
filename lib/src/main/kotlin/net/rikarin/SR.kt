package net.rikarin

const val CANNOT_RESOLVE_SERVICE = "Unable to resolve service for type '%s' while attempting to activate '%s'."
const val AMBIGUOUS_CONSTRUCTOR_EXCEPTION = "Unable to activate type '%s'. The following constructors are ambiguous: %s %s"
const val UNABLE_TO_ACTIVATE_TYPE_EXCEPTION = "No constructor for type '%s' can be instantiated using services from the service container and default values."
const val PROJECTED_TYPE_NOT_FOUND = "Projected type '%s' not found"
const val TYPE_CANNOT_BE_ACTIVATED = "Cannot instantiate implementation type '%s' for service type '%s'."
const val OPEN_GENERIC_SERVICE_REQUIRES_OPEN_GENERIC_IMPLEMENTATION = "Open generic service type '%s' requires registering an open generic implementation type."
const val DIRECT_SCOPED_RESOLVED_FROM_ROOT = "Cannot resolve %s service '%s' from root provider."
const val SCOPED_RESOLVED_FROM_ROOT = "Cannot resolve '%s' from root provider because it requires %s service '%s'."
const val SERVICE_COLLECTION_READ_ONLY = "The service collection cannot be modified because it is read-only."