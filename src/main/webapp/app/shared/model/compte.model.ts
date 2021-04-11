import { IPanier } from 'app/shared/model/panier.model';
import { IRoles } from 'app/shared/model/roles.model';
import { IRestaurant } from 'app/shared/model/restaurant.model';
import { ICooperative } from 'app/shared/model/cooperative.model';
import { ISystemePaiement } from 'app/shared/model/systeme-paiement.model';
import { ICourse } from 'app/shared/model/course.model';

export interface ICompte {
  id?: number;
  name?: string;
  surname?: string;
  age?: number;
  adress?: string;
  carts?: IPanier[];
  roles?: IRoles;
  owns?: IRestaurant;
  cooperative?: ICooperative;
  operations?: ISystemePaiement[];
  courses?: ICourse[];
}

export class Compte implements ICompte {
  constructor(
    public id?: number,
    public name?: string,
    public surname?: string,
    public age?: number,
    public adress?: string,
    public carts?: IPanier[],
    public roles?: IRoles,
    public owns?: IRestaurant,
    public cooperative?: ICooperative,
    public operations?: ISystemePaiement[],
    public courses?: ICourse[]
  ) {}
}
