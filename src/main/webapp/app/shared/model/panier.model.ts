import { IProduit } from 'app/shared/model/produit.model';
import { ICompte } from 'app/shared/model/compte.model';
import { IRestaurant } from 'app/shared/model/restaurant.model';

export interface IPanier {
  id?: number;
  nbElements?: number;
  price?: number;
  contents?: IProduit[];
  compte?: ICompte;
  restaurant?: IRestaurant;
}

export class Panier implements IPanier {
  constructor(
    public id?: number,
    public nbElements?: number,
    public price?: number,
    public contents?: IProduit[],
    public compte?: ICompte,
    public restaurant?: IRestaurant
  ) {}
}
