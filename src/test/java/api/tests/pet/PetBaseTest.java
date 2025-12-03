package api.tests.pet;

import api.base.BaseSetup;
import api.client.PetApi;

public class PetBaseTest extends BaseSetup {
    protected static final PetApi petApi = new PetApi(API_KEY);
}
