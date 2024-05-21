export interface IJhipster {
  id?: number;
  name?: string | null;
  closed?: boolean | null;
  setting?: string | null;
}

export const defaultValue: Readonly<IJhipster> = {
  closed: false,
};
