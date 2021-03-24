import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { IGiftItem, defaultValue } from 'app/shared/model/gift-item.model';

export const ACTION_TYPES = {
  FETCH_GIFTITEM_LIST: 'giftItem/FETCH_GIFTITEM_LIST',
  FETCH_GIFTITEM: 'giftItem/FETCH_GIFTITEM',
  CREATE_GIFTITEM: 'giftItem/CREATE_GIFTITEM',
  UPDATE_GIFTITEM: 'giftItem/UPDATE_GIFTITEM',
  DELETE_GIFTITEM: 'giftItem/DELETE_GIFTITEM',
  RESET: 'giftItem/RESET',
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IGiftItem>,
  entity: defaultValue,
  updating: false,
  updateSuccess: false,
};

export type GiftItemState = Readonly<typeof initialState>;

// Reducer

export default (state: GiftItemState = initialState, action): GiftItemState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_GIFTITEM_LIST):
    case REQUEST(ACTION_TYPES.FETCH_GIFTITEM):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true,
      };
    case REQUEST(ACTION_TYPES.CREATE_GIFTITEM):
    case REQUEST(ACTION_TYPES.UPDATE_GIFTITEM):
    case REQUEST(ACTION_TYPES.DELETE_GIFTITEM):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true,
      };
    case FAILURE(ACTION_TYPES.FETCH_GIFTITEM_LIST):
    case FAILURE(ACTION_TYPES.FETCH_GIFTITEM):
    case FAILURE(ACTION_TYPES.CREATE_GIFTITEM):
    case FAILURE(ACTION_TYPES.UPDATE_GIFTITEM):
    case FAILURE(ACTION_TYPES.DELETE_GIFTITEM):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload,
      };
    case SUCCESS(ACTION_TYPES.FETCH_GIFTITEM_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.FETCH_GIFTITEM):
      return {
        ...state,
        loading: false,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.CREATE_GIFTITEM):
    case SUCCESS(ACTION_TYPES.UPDATE_GIFTITEM):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.DELETE_GIFTITEM):
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

const apiUrl = 'api/gift-items';

// Actions

export const getEntities: ICrudGetAllAction<IGiftItem> = (page, size, sort) => ({
  type: ACTION_TYPES.FETCH_GIFTITEM_LIST,
  payload: axios.get<IGiftItem>(`${apiUrl}?cacheBuster=${new Date().getTime()}`),
});

export const getEntity: ICrudGetAction<IGiftItem> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_GIFTITEM,
    payload: axios.get<IGiftItem>(requestUrl),
  };
};

export const createEntity: ICrudPutAction<IGiftItem> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_GIFTITEM,
    payload: axios.post(apiUrl, cleanEntity(entity)),
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<IGiftItem> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_GIFTITEM,
    payload: axios.put(apiUrl, cleanEntity(entity)),
  });
  return result;
};

export const deleteEntity: ICrudDeleteAction<IGiftItem> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_GIFTITEM,
    payload: axios.delete(requestUrl),
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET,
});
