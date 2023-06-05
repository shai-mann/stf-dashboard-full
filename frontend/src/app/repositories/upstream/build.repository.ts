import {Observable} from "rxjs";
import {UpstreamSummaryModel} from "../../models/upstream-summary.model";
import {UpstreamInfoModel} from "../../models/upstream-info.model";
import {Sddc} from "../../models/meta/abstract-upstream.model";
import {BuildSummaryModel} from "../../models/build-summary.model";

/**
 * This repository is responsible for the management of Upstream Jobs.
 */
export abstract class BuildRepository {

  /**
   * Gets all upstream jobs under a given suite.
   * @param suite the suite to find upstream jobs for.
   * @param filter the FIQL filters applied to this query.
   */
  abstract getUpstreamJobs(suite: String, filter?: string[]): Observable<UpstreamSummaryModel[]>;

  /**
   * Gets more information about a given specific upstream job.
   * @param id the ID of the upstream job to find details on.
   */
  abstract getUpstreamJob(id: string): Observable<UpstreamInfoModel>;

  /**
   * Gets the number of tests under a given build with a given status. The build id is an upstream build id.
   * @param id the build id.
   */
  abstract getBuildSummary(id: string): Observable<Map<Sddc, BuildSummaryModel>>;

}
