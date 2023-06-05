/**
 * Contains relevant global tokens for the project.
 */
import {InjectionToken} from "@angular/core";

/**
 * The base URL for all REST API calls to the STF Dashboard backend.
 */
export const API_BASE_URL = new InjectionToken<string>('API_BASE_URL')
