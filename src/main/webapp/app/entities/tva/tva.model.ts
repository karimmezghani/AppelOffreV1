export interface ITva {
  id?: number;
  tauxTva?: number;
}

export class Tva implements ITva {
  constructor(public id?: number, public tauxTva?: number) {}
}

export function getTvaIdentifier(tva: ITva): number | undefined {
  return tva.id;
}
