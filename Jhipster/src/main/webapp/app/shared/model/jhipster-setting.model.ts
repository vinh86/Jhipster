import { IJhipster } from 'app/shared/model/jhipster.model';

export interface IJhipsterSetting {
  id?: number;
  theme?: string | null;
  pageSize?: number | null;
  others?: string | null;
  jhipster?: IJhipster;
}

export const defaultValue: Readonly<IJhipsterSetting> = {};
