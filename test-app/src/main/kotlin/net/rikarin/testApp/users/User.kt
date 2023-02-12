package net.rikarin.testApp.users

import net.rikarin.core.ID
import net.rikarin.domain.AggregateRootBase

class User : AggregateRootBase<ID>() {
    lateinit var name: String
        private set

}