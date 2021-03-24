export interface IGiftItem {
  id?: number;
  giftName?: string;
  descripption?: string;
  unitPrice?: number;
}

export const defaultValue: Readonly<IGiftItem> = {};
