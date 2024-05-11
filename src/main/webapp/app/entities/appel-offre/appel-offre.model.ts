import * as dayjs from 'dayjs';

export interface IAppelOffre {
  id?: number;
  numero?: string;
  dateDebut?: dayjs.Dayjs;
  dateFin?: dayjs.Dayjs;
  exercice?: string;
  designation?: string;
}

export class AppelOffre implements IAppelOffre {
  constructor(
    public id?: number,
    public numero?: string,
    public dateDebut?: dayjs.Dayjs,
    public dateFin?: dayjs.Dayjs,
    public exercice?: string,
    public designation?: string
  ) {}
}

export function getAppelOffreIdentifier(appelOffre: IAppelOffre): number | undefined {
  return appelOffre.id;
}
