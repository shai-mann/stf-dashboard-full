import {Observable} from "rxjs";

/**
 * This repository is responsible for the management of general application data collection.
 */
export abstract class ApplicationRepository {
  /**
   * Gets the supported SDDC types.
   */
  abstract getSddcTypes$(): Observable<String[]>;

  /**
   * Gets the supported test suites
   */
  abstract getSuiteTypes$(): Observable<String[]>;
}
