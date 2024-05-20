export interface IJhipster {
  id?: number;
  name?: string | null;
  closed?: boolean | null;
}

export const defaultValue: Readonly<IJhipster> = {
  closed: false,
};
