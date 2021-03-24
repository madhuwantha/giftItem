import { ICategory } from 'app/shared/model/category.model';
import { ICart } from 'app/shared/model/cart.model';

export interface IGiftItem {
  id?: number;
  giftName?: string;
  descripption?: string;
  unitPrice?: number;
  avalibleQuantity?: number;
  category?: ICategory;
  carts?: ICart[];
}

export const defaultValue: Readonly<IGiftItem> = {};
