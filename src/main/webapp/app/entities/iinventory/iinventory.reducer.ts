import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { IIinventory, defaultValue } from 'app/shared/model/iinventory.model';

export const ACTION_TYPES = {
  FETCH_IINVENTORY_LIST: 'iinventory/FETCH_IINVENTORY_LIST',
  FETCH_IINVENTORY: 'iinventory/FETCH_IINVENTORY',
  CREATE_IINVENTORY: 'iinventory/CREATE_IINVENTORY',
  UPDATE_IINVENTORY: 'iinventory/UPDATE_IINVENTORY',
  DELETE_IINVENTORY: 'iinventory/DELETE_IINVENTORY',
  RESET: 'iinventory/RESET',
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IIinventory>,
  entity: defaultValue,
  updating: false,
  updateSuccess: false,
};

export type IinventoryState = Readonly<typeof initialState>;

// Reducer

export default (state: IinventoryState = initialState, action): IinventoryState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_IINVENTORY_LIST):
    case REQUEST(ACTION_TYPES.FETCH_IINVENTORY):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true,
      };
    case REQUEST(ACTION_TYPES.CREATE_IINVENTORY):
    case REQUEST(ACTION_TYPES.UPDATE_IINVENTORY):
    case REQUEST(ACTION_TYPES.DELETE_IINVENTORY):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true,
      };
    case FAILURE(ACTION_TYPES.FETCH_IINVENTORY_LIST):
    case FAILURE(ACTION_TYPES.FETCH_IINVENTORY):
    case FAILURE(ACTION_TYPES.CREATE_IINVENTORY):
    case FAILURE(ACTION_TYPES.UPDATE_IINVENTORY):
    case FAILURE(ACTION_TYPES.DELETE_IINVENTORY):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload,
      };
    case SUCCESS(ACTION_TYPES.FETCH_IINVENTORY_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.FETCH_IINVENTORY):
      return {
        ...state,
        loading: false,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.CREATE_IINVENTORY):
    case SUCCESS(ACTION_TYPES.UPDATE_IINVENTORY):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.DELETE_IINVENTORY):
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

const apiUrl = 'api/iinventories';

// Actions

export const getEntities: ICrudGetAllAction<IIinventory> = (page, size, sort) => ({
  type: ACTION_TYPES.FETCH_IINVENTORY_LIST,
  payload: axios.get<IIinventory>(`${apiUrl}?cacheBuster=${new Date().getTime()}`),
});

export const getEntity: ICrudGetAction<IIinventory> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_IINVENTORY,
    payload: axios.get<IIinventory>(requestUrl),
  };
};

export const createEntity: ICrudPutAction<IIinventory> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_IINVENTORY,
    payload: axios.post(apiUrl, cleanEntity(entity)),
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<IIinventory> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_IINVENTORY,
    payload: axios.put(apiUrl, cleanEntity(entity)),
  });
  return result;
};

export const deleteEntity: ICrudDeleteAction<IIinventory> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_IINVENTORY,
    payload: axios.delete(requestUrl),
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET,
});
