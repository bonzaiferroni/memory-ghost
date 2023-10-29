package com.bollwerks.memoryghost.utils

import androidx.lifecycle.SavedStateHandle
import androidx.navigation.NamedNavArgument
import androidx.navigation.NavController
import androidx.navigation.NavType
import androidx.navigation.navArgument
import java.time.LocalDate

open class EzRoute(
    val name: String,
    vararg params: EzRouteParam<*>,
) {
    val route: String
    val params: List<EzRouteParam<*>> = params.toList()
    val navArguments: List<NamedNavArgument>

    init {
        this.route = buildRouteTemplate(name, params)
        this.navArguments = buildNavArguments(params)
    }

    fun navigate(navController: NavController?, vararg args: Pair<String, Any?>) {
        val route = buildRoute(args)
        navController?.navigate(route)
    }

    fun setEnumArg(key: String, value: Enum<*>): Pair<String, Any> {
        return key to value.toString()
    }

    inline fun <reified T : Enum<T>> getEnumArg(
        savedStateHandle: SavedStateHandle, key: String
    ): T? {
        val enumValue = savedStateHandle.get<String>(key) ?: return null
        return enumValueOf<T>(enumValue)
    }

    fun setDateArg(key: String, value: LocalDate): Pair<String, Any> {
        return key to value.toString()
    }

    fun getDateArg(savedStateHandle: SavedStateHandle, key: String): LocalDate? {
        val date = savedStateHandle.get<String>(key) ?: return null
        return LocalDate.parse(date)
    }

    private fun buildRoute(args: Array<out Pair<String, Any?>>): String {
        val sb = StringBuilder(this.name)
        var addedAmpersand = false
        for (i in this.params.indices) {
            val param = this.params[i]
            val arg = args.firstOrNull { it.first == param.name }
                ?: if (param.isOptional) continue
                else throw IllegalArgumentException("Missing required argument: ${param.name}")
            if (param.isOptional) {
                // optional
                if (arg.second == null) continue
                if (!addedAmpersand) {
                    sb.append('?')
                    addedAmpersand = true
                } else {
                    sb.append('&')
                }
                sb.append(arg.first)
                sb.append('=')
                sb.append(arg.second)
            } else {
                // required
                sb.append('/')
                sb.append(arg.second)
            }
        }
        return sb.toString()
    }

    private fun buildRouteTemplate(name: String, args: Array<out EzRouteParam<*>>): String {
        var route = name
        val optionalArgs = args.filter { it.isOptional }.map { it.name }
        val requiredArgs = args.filter { !it.isOptional }.map { it.name }
        requiredArgs.forEach { arg ->
            route += "/{$arg}"
        }
        if (optionalArgs.isNotEmpty()) {
            route += "?"
            for (i in optionalArgs.indices) {
                val argName = optionalArgs[i]
                route += if (i == 0) {
                    "$argName={$argName}"
                } else {
                    "&$argName={$argName}"
                }
            }
        }
        return route
    }

    private fun buildNavArguments(args: Array<out EzRouteParam<*>>): List<NamedNavArgument> {
        return args.map { arg ->
            if (arg.isOptional) {
                navArgument(arg.name) {
                    type = arg.type
                    defaultValue = arg.defaultValue
                }
            } else {
                navArgument(arg.name) {
                    type = arg.type
                }
            }

        }
    }
}

open class EzRouteParam<T>(
    val name: String,
    val type: NavType<T>,
    val defaultValue: T? = null,
    val isOptional: Boolean = false,
)

class EzEnumParam<T : Enum<T>>(
    name: String,
    defaultValue: T? = null,
    isOptional: Boolean = false,
) : EzRouteParam<String?>(
    name = name,
    type = NavType.StringType,
    defaultValue = defaultValue?.toString(),
    isOptional = isOptional,
)

class EzDateParam(
    name: String,
    defaultValue: LocalDate? = null,
    isOptional: Boolean = false,
) : EzRouteParam<String?>(
    name = name,
    type = NavType.StringType,
    defaultValue = defaultValue?.toString(),
    isOptional = isOptional,
)
