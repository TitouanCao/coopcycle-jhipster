import { ICompte } from 'app/shared/model/compte.model';
import { IProduit } from 'app/shared/model/produit.model';
import { ICourse } from 'app/shared/model/course.model';
import { IPanier } from 'app/shared/model/panier.model';
import { ICooperative } from 'app/shared/model/cooperative.model';

export interface IRestaurant {
  id?: number;
  name?: string;
  adress?: string;
  owned?: ICompte;
  products?: IProduit[];
  orders?: ICourse[];
  carts?: IPanier[];
  cooperative?: ICooperative;
}

export class Restaurant implements IRestaurant {
  constructor(
    public id?: number,
    public name?: string,
    public adress?: string,
    public owned?: ICompte,
    public products?: IProduit[],
    public orders?: ICourse[],
    public carts?: IPanier[],
    public cooperative?: ICooperative
  ) {}
}
