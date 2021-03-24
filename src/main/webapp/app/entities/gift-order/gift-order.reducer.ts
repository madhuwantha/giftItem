import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { IGiftOrder, defaultValue } from 'app/shared/model/gift-order.model';

export const ACTION_TYPES = {
  FETCH_GIFTORDER_LIST: 'giftOrder/FETCH_GIFTORDER_LIST',
  FETCH_GIFTORDER: 'giftOrder/FETCH_GIFTORDER',
  CREATE_GIFTORDER: 'giftOrder/CREATE_GIFTORDER',
  UPDATE_GIFTORDER: 'giftOrder/UPDATE_GIFTORDER',
  DELETE_GIFTORDER: 'giftOrder/DELETE_GIFTORDER',
  RESET: 'giftOrder/RESET',
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IGiftOrder>,
  entity: defaultValue,
  updating: false,
  updateSuccess: false,
};

export type GiftOrderState = Readonly<typeof initialState>;

// Reducer

export default (state: GiftOrderState = initialState, action): GiftOrderState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_GIFTORDER_LIST):
    case REQUEST(ACTION_TYPES.FETCH_GIFTORDER):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true,
      };
    case REQUEST(ACTION_TYPES.CREATE_GIFTORDER):
    case REQUEST(ACTION_TYPES.UPDATE_GIFTORDER):
    case REQUEST(ACTION_TYPES.DELETE_GIFTORDER):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true,
      };
    case FAILURE(ACTION_TYPES.FETCH_GIFTORDER_LIST):
    case FAILURE(ACTION_TYPES.FETCH_GIFTORDER):
    case FAILURE(ACTION_TYPES.CREATE_GIFTORDER):
    case FAILURE(ACTION_TYPES.UPDATE_GIFTORDER):
    case FAILURE(ACTION_TYPES.DELETE_GIFTORDER):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload,
      };
    case SUCCESS(ACTION_TYPES.FETCH_GIFTORDER_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.FETCH_GIFTORDER):
      return {
        ...state,
        loading: false,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.CREATE_GIFTORDER):
    case SUCCESS(ACTION_TYPES.UPDATE_GIFTORDER):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.DELETE_GIFTORDER):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: {},
      };
    case ACTION_TYPES.RESET:
      return {
        ...initialState,
      };
    default:
      return state;
  }
};

const apiUrl = 'api/gift-orders';

// Actions

export const getEntities: ICrudGetAllAction<IGiftOrder> = (page, size, sort) => ({
  type: ACTION_TYPES.FETCH_GIFTORDER_LIST,
  payload: axios.get<IGiftOrder>(`${apiUrl}?cacheBuster=${new Date().getTime()}`),
});

export const getEntity: ICrudGetAction<IGiftOrder> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_GIFTORDER,
    payload: axios.get<IGiftOrder>(requestUrl),
  };
};

export const createEntity: ICrudPutAction<IGiftOrder> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_GIFTORDER,
    payload: axios.post(apiUrl, cleanEntity(entity)),
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<IGiftOrder> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_GIFTORDER,
    payload: axios.put(apiUrl, cleanEntity(entity)),
  });
  return result;
};

export const deleteEntity: ICrudDeleteAction<IGiftOrder> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_GIFTORDER,
    payload: axios.delete(requestUrl),
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET,
});
