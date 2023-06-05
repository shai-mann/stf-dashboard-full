/**
 * An abstraction of the model types that can exist for Upstream objects.
 */
export interface AbstractUpstreamModel<T> {

  jobId: number;

  buildId: number;

  buildNumber: number;

  ob: number;

  name: string;

  buildTimestamp: string;

  status: string;

  results: Map<Sddc, T>;

}

export enum Suite {
  SMOKE = "Smoke",
  VDC_SERVICE_A = "VdcService-A",
  VDC_SERVICE_B = "VdcService-B",
  VDC_SERVICE_C = "VdcService-C",
  VDC_SERVICE_D = "VdcService-D",
  NETWORKING = "Networking",
  MULTITENANCY = "Multitenancy",
}

export enum Sddc {
  VMC = "VMC",
  AVS = "AVS",
  OCVS = "OCVS",
  GCVE = "GCVE",
}
