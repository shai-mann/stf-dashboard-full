import {Suite} from "./abstract-upstream.model";

/**
 * An abstraction of the model types that can exist for Test result objects.
 */
export interface AbstractTestModel<T> {

  id: number;

  name: string;

  parameters: string;

  packagePath: string;

  className: string;

  results: Map<Suite, T>;

}
