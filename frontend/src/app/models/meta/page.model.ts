/**
 * An abstract model for a Page of objects.
 */
export interface PageModel<T> {

  content: T[];

  totalElements: number;

}
