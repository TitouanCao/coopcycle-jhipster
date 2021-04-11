import { ICompte } from 'app/shared/model/compte.model';

export interface ISystemePaiement {
  id?: number;
  method?: string;
  agents?: ICompte[];
}

export class SystemePaiement implements ISystemePaiement {
  constructor(public id?: number, public method?: string, public agents?: ICompte[]) {}
}
